# Fabricated Rift
Fabricated Rift is a port of the [Rift](https://github.com/Chocohead/Rift) mod loader for Minecraft 1.13.2 that runs as a regular mod on [Fabric Loader](https://github.com/FabricMC/fabric-loader). It lets you play Rift mods alongside Fabric mods, using either [Ornithe](https://ornithemc.net/) or [Legacy Fabric](https://legacyfabric.net/).

Rift itself is a lightweight modding API, library and mod loader for Minecraft 1.13. Its main objective was to provide a more modular and lightweight modding API. Rift originally shipped as a LaunchWrapper tweaker. This project modifies it so it runs inside Fabric Loader instead. Rift mods are discovered and remapped at launch through [Mod Remapping API](https://modrinth.com/mod/mod-remapping-api), so they work unchanged.

Fabricated Rift is the recommended way to run Rift mods today.

## Playing
### Requirements
* Minecraft 1.13.2
* Fabric Loader 0.18.3 or newer, installed through either:
  * [Ornithe](https://ornithemc.net/) (Gen2), or
  * [Legacy Fabric](https://legacyfabric.net/)
* [Mod Remapping API](https://modrinth.com/mod/mod-remapping-api)

### Installing
1. Create a Minecraft 1.13.2 instance with Fabric Loader, using the Ornithe or Legacy Fabric installer (or your launcher's built-in support for either).
2. Download Fabricated Rift from [Modrinth](https://modrinth.com/mod/fabricated-rift) and put it in the `mods` folder along with Mod Remapping API.
3. Put your Rift mods in the same `mods` folder. They are detected by their `riftmod.json` and loaded automatically.
4. Launch the game.

### Optional compatibility
* **Ornithe Standard Libraries (OSL):** supported on Ornithe. Version 0.23.0 or newer is required if installed.
* **Mod Menu:** when installed, Rift mods appear in its mod list and their config screens are reachable from there (this replaces Rift Mod List).
* **OptiFine:** supported through [Legacy OptiFabric](https://modrinth.com/mod/legacy-optifabric).

If you run into problems or incompatibilities, please report them on the [issue tracker](https://github.com/FabricCompatibilityLayers/Fabricated-Rift/issues).

## Developing Rift mods
You can still make Rift mods. The original Forge Gradle-based toolchain is no longer maintained, so use [Unimined](https://github.com/unimined/unimined) instead, which supports Rift as a target platform.

### API changes between Rift 1.13 and 1.13.2
Obfuscated mappings changed between versions, so any access transformers need updating. A tool for doing this automatically is available [here](https://github.com/Chocohead/Rift/blob/newerest/src/debug/java/com/chocohead/rift/AccessTransformerUpdater.java). It can also be used to add access transformer entries more easily, as it goes straight from MCP names to the matching Notch names.

Rift's API also changed to account for changes made in Minecraft 1.13.1:
* The deprecated `CustomPayloadHandler` has been removed in favour of `MessageAdder`.
* `DimensionTypeAdder` no longer expects a set of `DimensionType`s. Because `DimensionType` is no longer an enum, it is now a callback for registering them directly, with a utility method `addDimensionType` for this purpose.
* `ClientTickable#tick` now provides the `Minecraft` client instance directly, matching `ServerTickable#tick` providing `MinecraftServer`.

Many MCP method names changed too, but most (if not all) are obvious enough not to list here.

## Credits
* [Rift](https://github.com/DimensionalDevelopment/Rift) by Runemoro and the DimDev team.
* The [1.13.1 and 1.13.2 ports of Rift](https://github.com/Chocohead/Rift) by Chocohead, which this project is based on.