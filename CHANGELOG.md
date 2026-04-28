# Changelog — Voxy NeoForge 1.21.1

All changes to the Voxy NeoForge 1.21.1 port.

---

## [0.2.14-NeoForge-1.21.1] — 2026-04-26

### Added — Physics Mod Compatibility
- `PhysicsModCompat.java` — detects Physics Mod via `FabricLoader.isModLoaded("physicsmod")`, reflects ocean config
- `SoftwareModelTextureBakery.bakeWaterTopFaceFallback()` — manually emits `block/water_still` sprite for UP face when Physics Mod suppresses vanilla water rendering
- Physics Mod ocean renders correctly alongside Voxy LOD terrain (ocean restricted to vanilla render distance by design)

### Upstream Synced (4 commits)
- **`336c201`** — biome colour fix: always register model for biome colours even when biome list is empty
- **`260bcdb`** — dynamic model layer detection: inspects baked texture pixels to determine solid/translucent/cutout instead of trusting render type flags
- **`a006364`** — rasterizer blending + triangle seam fix: software alpha blending, overlapping triangle seam correction
- **`a4c1ab2`** — rasterizer framebuffer size via constructor: cleaner architecture, `TARGET_SIZE` removed

### Changed
- Jar renamed to `voxy-0.2.14-NeoForge-1.21.1.jar`

---

## [0.2.14-NeoForge-1.21.1-initial] — 2026-04-26

### Initial Port
- NeoForge 1.21.1 port of Voxy based on original Fabric mod by Cortex
- Includes Fix-sodium-ShaderLoader for NeoForge compatibility
- Iris shader pipeline support
- Full Sodium integration
