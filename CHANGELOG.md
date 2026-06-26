# Changelog - Voxy NeoForge 1.21.1

All changes to the Voxy NeoForge 1.21.1 port.

---

## [0.2.15-NeoForge-1.21.1] - 2026-06-19

### Fixed
- **Serialization NPE** - `collectAllClasses(String)` no longer throws `NullPointerException` on every launch under NeoForge/Connector (classloader returns `null` for package directory listings in JAR-based environments)
- **MixinSodiumOptionsGUI** - deleted entirely; it is unreachable under Connector and causes `@Mixin target not found` errors
- Addressed `NoClassDefFoundError` for Sodium's `OptionStorage` on startup by decoupling `VoxyConfig` interface hierarchy
- Wrapped Sodium options integration in `VoxyConfigOptionStorage` singleton
- Fixed `NoClassDefFoundError` in `Serialization.java` on `VoxyConfigOptionStorage` when starting up (second pass)
- Restored Voxy video settings page integration by bypassing Mixin target class check (which falsely failed on Connector)
- **Sodium 0.8 Compatibility** - Added `ValueFormatter` to all `Integer` options and `ElementNameProvider` to `Enum` options as required by Sodium 0.8's strict config rules
- **Sodium 0.8 Compatibility** - Updated `MixinRenderSectionManager` injection signature to include the new `SortBehavior` parameter in `RenderSectionManager`'s constructor
- **Sodium 0.8 Compatibility** - Updated `MixinDefaultChunkRenderer` injection signatures for the `render` method to accommodate the new `hasFrustum` boolean parameter added in Sodium 0.8
- **Config Translation** - Fixed missing translation for the `enable_rendering` option by correcting the translation key to match `en_us.json`
### Removed
- **MixinShaderLoader** - deleted dead mixin; Sodium 0.8+ already uses `getClassLoader().getResourceAsStream()` natively, making the redirect a no-op
- **VoxyConfigMenu entrypoint** - removed `sodium:config_api_user` from `fabric.mod.json` referencing nonexistent `VoxyConfigMenu` class

### Changed
- `connectorInstalled` flag removed from `ClientVoxyMixinPlugin` (was only used for the now-deleted `MixinShaderLoader`)
- Jar renamed to `voxy-0.2.15-NeoForge-1.21.1.jar`

---

## [0.2.14-NeoForge-1.21.1] - 2026-04-26

### Added - Physics Mod Compatibility
- `PhysicsModCompat.java` - detects Physics Mod via `FabricLoader.isModLoaded("physicsmod")`, reflects ocean config
- `SoftwareModelTextureBakery.bakeWaterTopFaceFallback()` - manually emits `block/water_still` sprite for UP face when Physics Mod suppresses vanilla water rendering
- Physics Mod ocean renders correctly alongside Voxy LOD terrain (ocean restricted to vanilla render distance by design)

### Upstream Synced (4 commits)
- **`336c201`** - biome colour fix: always register model for biome colours even when biome list is empty
- **`260bcdb`** - dynamic model layer detection: inspects baked texture pixels to determine solid/translucent/cutout instead of trusting render type flags
- **`a006364`** - rasterizer blending + triangle seam fix: software alpha blending, overlapping triangle seam correction
- **`a4c1ab2`** - rasterizer framebuffer size via constructor: cleaner architecture, `TARGET_SIZE` removed

### Changed
- Jar renamed to `voxy-0.2.14-NeoForge-1.21.1.jar`

---

## [0.2.14-NeoForge-1.21.1-initial] - 2026-04-26

### Initial Port
- NeoForge 1.21.1 port of Voxy based on original Fabric mod by Cortex
- Includes Fix-sodium-ShaderLoader for NeoForge compatibility
- Iris shader pipeline support
- Full Sodium integration
