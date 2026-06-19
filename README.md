# Voxy - NeoForge 1.21.1 Port

> **Far-distance LoD rendering for Minecraft 1.21.1 - NeoForge build**

Voxy renders distant terrain using Level-of-Detail (LoD) chunks, allowing you to see far beyond Minecraft's normal render distance without the performance cost of loading full chunks. This is the **NeoForge 1.21.1** port (`v0.2.15-NeoForge-1.21.1`), based on the original Fabric mod by [Cortex](https://github.com/CortexMC).


> **Works best with:**
> 🌍 [Voxy World Gen V2](https://github.com/realBritakee/voxy_worldgen_v2) - background chunk pre-generation for 1.20.1/1.21.1 · [other versions](https://modrinth.com/mod/voxy-worldgen)
> 🎨 [Photon Shaders - Reimagined](https://github.com/realBritakee/photon) - custom Photon fork with Physics Mod ocean support & Colorwheel (Create)

---

## Features

- Far-distance LoD rendering using OpenGL 4.6 compute shaders
- Hierarchical chunk culling for performance
- Iris shader support (compatible with shaderpack pipelines)
- Nvidium compatibility
- SSAO (Screen Space Ambient Occlusion) in the distance
- Configurable via in-game settings screen (Sodium options integration)
- Includes **Physics Mod ocean compatibility** - ocean waves render correctly alongside Voxy LoD terrain within your render distance

  > **⚠️ Physics Mod ocean limitation:** Wave simulation only applies within your default Minecraft render distance. Chunks further out rendered by Voxy's LoD system will show as normal flat water without physics - this is a Physics Mod limitation, not a Voxy issue.
- Includes **Fix-sodium-ShaderLoader** - fixes Sodium's `ShaderLoader.getResourceAsStream` on NeoForge so Voxy and Nvidium load shaders correctly (based on [coco875/Fix-sodium-ShaderLoader](https://github.com/coco875/Fix-sodium-ShaderLoader), merged directly into this build)

---

## Requirements

### Fabric

| Dependency | Version | Notes |
|---|---|---|
| Minecraft | 1.21.1 | |
| Fabric Loader | ≥ 0.17.2 | |
| Fabric API | 0.116.6+1.21.1 | |
| Sodium | ≥ 0.6.13 | Required - Voxy hooks into Sodium's renderer |
| Java | 17+ | Shipped with Minecraft 1.21.1 |
| OpenGL | 4.6 | GPU must support OpenGL 4.6 (most GPUs from 2017+) |

### NeoForge

| Dependency | Version | Notes |
|---|---|---|
| Minecraft | 1.21.1 | |
| NeoForge | 21.1.x | |
| Sinytra Connector | latest | Bridges Fabric mods to NeoForge |
| Forgified Fabric API | latest | NeoForge port of Fabric API, required by Connector |
| Sodium (NeoForge) | ≥ 0.8.12 | Required - use the NeoForge build of Sodium |
| Java | 17+ | Shipped with Minecraft 1.21.1 |
| OpenGL | 4.6 | GPU must support OpenGL 4.6 (most GPUs from 2017+) |

---

## Building

Requires **JDK 17+** and **Gradle** (wrapper included).

```bash
# Clone the repo
git clone https://github.com/your-repo/voxy-neoforge.git
cd voxy-neoforge

# Build
./gradlew build
```

Output jar: `build/libs/voxy-<version>.jar`

On Windows (PowerShell):
```powershell
.\gradlew.bat build
```

---

## Installation

### Fabric
1. Install [Fabric Loader](https://fabricmc.net/use/) ≥ 0.17.2 for Minecraft 1.21.1
2. Install [Fabric API](https://modrinth.com/mod/fabric-api) 0.116.6+1.21.1
3. Install [Sodium for Fabric](https://modrinth.com/mod/sodium) (≥ 0.6.13)
4. Drop `voxy-<version>.jar` into your `mods/` folder
5. Launch and configure via **Options → Video Settings → Voxy**

### NeoForge
1. Install [NeoForge 21.1.x](https://neoforged.net/) for Minecraft 1.21.1
2. Install [Sinytra Connector](https://modrinth.com/mod/connector)
3. Install [Forgified Fabric API](https://modrinth.com/mod/forgified-fabric-api)
4. Install [Sodium for NeoForge](https://modrinth.com/mod/sodium) (≥ 0.8.12)
5. Drop `voxy-<version>.jar` into your `mods/` folder
6. Launch and configure via **Options → Video Settings → Voxy**

---

## Included Fixes

### Fix-sodium-ShaderLoader
Sodium's `ShaderLoader.getShaderSource()` uses `Class.getResourceAsStream()` which fails on NeoForge's classloader. This build includes a Mixin that redirects it to `ClassLoader.getResourceAsStream()`, fixing shader loading for both Voxy and Nvidium on NeoForge.

**Source:** `src/main/java/me/cortex/voxy/client/mixin/sodium/MixinShaderLoader.java`
**Credit:** [coco875](https://github.com/coco875/Fix-sodium-ShaderLoader)

---

## Project Structure

```
src/main/java/me/cortex/voxy/
├── client/
│   ├── core/           - Render pipeline, GL abstractions, shader system
│   ├── mixin/          - Mixins for Sodium, Iris, Nvidium, Minecraft, Flashback
│   │   └── sodium/     - Sodium-specific mixins incl. ShaderLoader fix
│   ├── config/         - VoxyConfig, settings screen
│   └── compat/         - Flashback, Iris compat
└── commonImpl/         - Shared world/chunk management logic
```

---

## Version

| Property | Value |
|---|---|
| Mod version | 0.2.15-NeoForge-1.21.1 |
| Minecraft | 1.21.1 |
| Fabric Loader | 0.17.2 |
| Fabric API | 0.116.6+1.21.1 |
| Group | me.cortex |
| Archive name | voxy |

---

## Changelog

See [CHANGELOG.md](CHANGELOG.md) for a full list of changes.

---

## License

All Rights Reserved - original mod by Cortex. NeoForge port by britakee.

---

## Related Projects

### Voxy World Gen V2
Background chunk pre-generation for Voxy - silently generates and auto-ingests chunks into Voxy's LOD system.

- **1.20.1 / 1.21.1** → [github.com/realBritakee/voxy_worldgen_v2](https://github.com/realBritakee/voxy_worldgen_v2) *(custom port)*
- **Other versions** → [modrinth.com/mod/voxy-worldgen](https://modrinth.com/mod/voxy-worldgen) *(official)*

### Photon Shaders - Reimagined
Custom Photon fork with native Physics Mod ocean support and Colorwheel (Create) shading - fully compatible with this Voxy build.

- **All versions** → [github.com/realBritakee/photon](https://github.com/realBritakee/photon) *(always up to date)*
