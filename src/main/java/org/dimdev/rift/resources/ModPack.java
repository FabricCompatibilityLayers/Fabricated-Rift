package org.dimdev.rift.resources;

import com.google.common.collect.Lists;
import net.fabricmc.loader.impl.util.FileSystemUtil;
import net.fabricmc.loader.impl.util.UrlUtil;
import net.minecraft.resources.AbstractResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.function.Predicate;

public class ModPack extends AbstractResourcePack {
    private final Path root;
    private final String name;
    private Logger LOGGER = LogManager.getLogger();
    private final String separator;

    public ModPack(String name, URL root) {
        this(name, Objects.requireNonNull(getRootAsPath(root)));
    }

    public ModPack(String name, Path root) {
        super(null);
        this.name = name;
        this.root = root;
        this.separator = root.getFileSystem().getSeparator();
    }

    private static Path getRootAsPath(URL root) {
        try {
            FileSystemUtil.FileSystemDelegate delegate = FileSystemUtil.getJarFileSystem(root.toURI(), false);
            FileSystem fs = delegate.get();

            if (fs == null) {
                throw new RuntimeException("Could not open JAR file " + root + " for NIO reading!");
            }

            return fs.getRootDirectories().iterator().next();
        } catch (Throwable e) {
            return null;
        }
    }

    private Path getPath(String filename) {
        Path childPath = root.resolve(filename.replace("/", separator)).toAbsolutePath().normalize();

        if (childPath.startsWith(root) && Files.exists(childPath)) {
            return childPath;
        } else {
            return null;
        }
    }

    @Override
    protected InputStream getInputStream(String filename) throws IOException {
        Path path = getPath(filename);

        if (path != null && Files.isRegularFile(path)) {
            return Files.newInputStream(path);
        }

        // ReloadableResourceManagerImpl gets away with FileNotFoundException.
        throw new FileNotFoundException("\"" + filename + "\" in Rift mod \"" + this.name + "\"");
    }

    @Override
    protected boolean resourceExists(String filename) {
        Path path = getPath(filename);
        return path != null && Files.isRegularFile(path);
    }

    @Override
    public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType type, String path, int maxDepth, Predicate<String> filter) {
        Set<ResourceLocation> resourceLocations = new HashSet<>();
        for (String namespace : getResourceNamespaces(type)) {
            resourceLocations.addAll(getAllResourceLocations(type, new ResourceLocation(namespace, path), maxDepth, filter));
        }
        return resourceLocations;
    }

    public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType type, ResourceLocation location, int maxDepth, Predicate<String> filter) {
        Set<ResourceLocation> resourceLocations = new HashSet<>();

        try {
            String path = String.format("%s/%s/%s", type.getDirectoryName(), location.getNamespace(), location.getPath());
            Path url = getPath(path);
            resourceLocations.addAll(getAllResourceLocations(maxDepth, location, url, filter));
        } catch (IOException e) {
            LOGGER.error("Couldn't get a list of all resources of '" + getName() + "'", e);
        }

        return resourceLocations;
    }

    private static Collection<ResourceLocation> getAllResourceLocations(int maxDepth, ResourceLocation rootLocation, Path rootPath, Predicate<String> filter) throws IOException {
        List<ResourceLocation> resourceLocations = Lists.newArrayList();
        Iterator<Path> pathIterator = Files.walk(rootPath, maxDepth).iterator();

        while (pathIterator.hasNext()) {
            Path path = pathIterator.next();
            if (path != null && !path.endsWith(".mcmeta") && Files.isRegularFile(path) && filter.test(path.getFileName().toString())) {
                resourceLocations.add(new ResourceLocation(rootLocation.getNamespace(), rootLocation.getPath() + "/" + rootPath.toAbsolutePath().relativize(path).toString().replaceAll("\\\\", "/")));
            }
        }

        return resourceLocations;
    }

    @Override
    public Set<String> getResourceNamespaces(ResourcePackType type) {
        try {
            Path typePath = getPath(type.getDirectoryName());

            if (typePath == null || !(Files.isDirectory(typePath))) {
                return Collections.emptySet();
            }

            Set<String> namespaces = new HashSet<>();

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(typePath, Files::isDirectory)) {
                for (Path path : stream) {
                    String s = path.getFileName().toString();
                    // s may contain trailing slashes, remove them
                    s = s.replace(separator, "");

                    namespaces.add(s);
                }
            }

            return namespaces;
        } catch (IOException e) {
            LOGGER.error("Couldn't get a list of resource namespaces of '" + getName() + "'", e);
        }

        return Collections.emptySet();
    }

    @Override
    public void close() {}

    @Override
    public String getName() {
        return name;
    }
}
