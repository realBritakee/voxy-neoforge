# Changelog — Voxy Forge 1.20.1

All changes to the Voxy Forge 1.20.1 port.

---

## [0.2.14-Forge-1.20.1] — 2026-04-26

### Added — Physics Mod Compatibility
- `PhysicsModCompat.java` — detects Physics Mod via `FabricLoader.isModLoaded("physicsmod")`, reflects ocean config
- `SoftwareModelTextureBakery.bakeWaterTopFaceFallback()` — manually emits `block/water_still` sprite for UP face when Physics Mod suppresses vanilla water rendering
- Physics Mod ocean renders correctly alongside Voxy LOD terrain

### Added — Java 17 Compatibility
- Rewrote `ExpansionUtil.java` with software PDEP/PEXT loops (no `java.lang.Long.compress`/`expand` needed)
- Removed `java21` source set and Multi-Release JAR manifest
- Now runs on Java 17+ (Gradle still requires Java 21 via fabric-loom 1.13)

### Upstream Synced (4 commits)
- **`336c201`** — biome colour fix
- **`260bcdb`** — dynamic model layer detection
- **`a006364`** — rasterizer blending + triangle seam fix
- **`a4c1ab2`** — rasterizer framebuffer size via constructor

### Fixed
- `VoxyCommon.gitCommitHash()` crash on empty git hash — guarded with `commit.length() >= 7` check

### Changed
- Jar renamed to `voxy-0.2.14-Forge-1.20.1.jar`

---

## [0.2.14-Forge-1.20.1-initial] — 2026-04-26

### Initial Port
- Forge 1.20.1 port of Voxy (via Fabric + Connector)
- Embeddium/Oculus integration
- Iris/Oculus shader pipeline support
