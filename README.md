# Voxy — Fabric 1.20.1 (Forge-compatible via Connector)

> **Far-distance LoD rendering for Minecraft 1.20.1 — Fabric mod with Forge support via Connector**

Voxy renders distant terrain using Level-of-Detail (LoD) chunks, allowing you to see far beyond Minecraft's normal render distance without the performance cost of loading full chunks. This is the **Fabric 1.20.1** build (`v0.2.14-Forge-1.20.1`), based on the original by [Cortex](https://github.com/CortexMC).


> **Works best with:**
> 🌍 [Voxy World Gen V2](https://github.com/realBritakee/voxy_worldgen_v2) — background chunk pre-generation for 1.20.1/1.21.1 · [other versions](https://modrinth.com/mod/voxy-worldgen)
> 🎨 [Photon Shaders — Reimagined](https://github.com/realBritakee/photon) — custom Photon fork with Physics Mod ocean support & Colorwheel (Create)

---

## Features

- Far-distance LoD rendering using OpenGL 4.6 compute shaders
- Hierarchical chunk culling for performance
- Iris shader support (compatible with shaderpacks like Photon)
- Oculus shader mod support
- Embeddium & Oculus optimization
- SSAO (Screen Space Ambient Occlusion) in the distance
- Configurable via in-game settings screen
- Works on **Fabric** and **Forge** (via Connector + Forgified Fabric API)
- Includes **Physics Mod ocean compatibility** — ocean renders correctly alongside Voxy LOD terrain
- **Java 17 compatible** — no Java 21 required at runtime

---

## Installation

### Fabric 1.20.1

1. Install [Fabric Loader](https://fabricmc.net/use/) ≥ 0.14.22 for Minecraft 1.20.1
2. Install [Fabric API](https://modrinth.com/mod/fabric-api) 0.92.6+1.20.1
3. Install [Embeddium](https://modrinth.com/mod/embeddium) (Sodium replacement for 1.20.1) or [Sodium](https://modrinth.com/mod/sodium)
4. Drop `voxy-0.2.14-Forge-1.20.1.jar` into your `mods/` folder
5. **(Optional)** Install [Oculus](https://modrinth.com/mod/oculus) for shader support
6. **(Optional)** Install a shader pack (e.g., [Photon](https://modrinth.com/shaderpack/photon))
7. Launch and configure via **Options → Video Settings → Voxy**

### Forge 1.20.1 (via Connector)

1. Install [Forge 1.20.1](https://files.minecraftforge.net/net/minecraftforge/forge/) (47.4.20+)
2. Install [Sinytra Connector](https://modrinth.com/mod/connector) (beta.47+)
3. Install [Forgified Fabric API](https://modrinth.com/mod/forgified-fabric-api) 0.92.6+1.20.1
4. Install [Embeddium](https://modrinth.com/mod/embeddium) (as Fabric mod via Connector)
5. Drop `voxy-0.2.14-Forge-1.20.1.jar` into your `mods/` folder
6. **(Optional)** Install [Oculus](https://modrinth.com/mod/oculus) (Fabric) for shader support
7. **(Optional)** Install a shader pack (e.g., [Photon](https://modrinth.com/shaderpack/photon))
8. Launch and configure via **Options → Video Settings → Voxy**

---

## System Requirements

| Requirement | Minimum |
|---|---|
| **Minecraft** | 1.20.1 |
| **Java** | Java 17+ (21+ recommended) |
| **OpenGL** | 4.6 (GPUs from 2017+) |
| **RAM** | 4GB+ allocated (8GB+ recommended for Voxy + shaders) |
| **GPU** | 2GB+ VRAM recommended |

---

## Tested Configuration ✅

| Component | Version | Status |
|---|---|---|
| Minecraft | 1.20.1 | ✅ |
| Loader | Fabric 0.17.2 | ✅ |
| Connector | beta.47+ | ✅ (Forge support) |
| Embeddium | Latest | ✅ (Sodium replacement) |
| Oculus | Latest | ✅ (Shader mod) |
| Photon Shader | 1.3b | ✅ Tested & working |
| Java | 21 | ✅ |

---

## Building

Requires **JDK 21** and **Gradle** (wrapper included).

```bash
# Clone the repo
git clone https://github.com/your-repo/voxy-neoforge-1211.git
cd voxy-neoforge-1211

# Build
./gradlew build
```

Output jar: `build/libs/voxy-0.2.14-Forge-1.20.1.jar`

On Windows (PowerShell):
```powershell
.\gradlew.bat build
```

---

## Mod Configuration

After launching, configure Voxy via:
- **Options → Video Settings → Voxy**
- Or in-game pause menu

---

## Shader Packs

Voxy is compatible with Iris shaderpacks. Tested with:
- **Photon 1.3b** ✅ Working

Install via [Modrinth](https://modrinth.com/shaders) and select in **Iris Options → Shaders**.

---

## Project Structure

```
src/main/java/me/cortex/voxy/
├── client/
│   ├── core/           — Render pipeline, GL abstractions, shader system
│   ├── mixin/          — Mixins for Sodium/Embeddium, Iris, Nvidium, Minecraft
│   ├── config/         — VoxyConfig, settings screen
│   ├── compat/         — Flashback, Iris compatibility
│   └── ClientImportManager.java  — World import/export
└── commonImpl/          — Shared world/chunk management logic

src/main/resources/
└── assets/voxy/shaders/  — 50+ GLSL shader files (compute, vertex, fragment)
```

---

## Version Info

| Property | Value |
|---|---|
| **Mod Version** | 0.2.14-alpha |
| **Build Output** | `voxy-0.2.14-Forge-1.20.1.jar` |
| **Minecraft** | 1.20.1 |
| **Fabric Loader** | 0.17.2 |
| **Fabric API** | 0.92.6+1.20.1 |
| **Group ID** | me.cortex |

---

## Known Issues

- Sodium dependency marked as TODO (may be removed in future versions)
- Some optional mods may have missing mixin targets (non-blocking warnings)
- Oculus forces some Sodium mixins to disable (expected, no issues)

---

## Troubleshooting

### Game won't start
- Ensure Java 21 is set in your launcher settings
- Check OpenGL 4.6 support: update GPU drivers

### Voxy settings not appearing
- Press ESC → Options → Video Settings → scroll to find "Voxy"
- May require ModMenu for easier access

### Shaders not loading
- Ensure Oculus is installed
- Install shader pack via Modrinth
- Check Iris options in pause menu

### Connector compatibility issues
- Update Connector to beta.47+
- Ensure Forgified Fabric API is installed
- Verify all Fabric mods are present before launching

---

## Credits

- **Original Voxy**: [Cortex](https://github.com/CortexMC)
- **Fabric Port & Connector Support**: britakee
- **Shader packs**: Rre36 (Photon) and community creators

---

## Changelog

See [CHANGELOG.md](CHANGELOG.md) for a full list of changes.

---

## License

---

## Related Projects

### Voxy World Gen V2
Background chunk pre-generation for Voxy — silently generates and auto-ingests chunks into Voxy's LOD system.

- **1.20.1 / 1.21.1** → [github.com/realBritakee/voxy_worldgen_v2](https://github.com/realBritakee/voxy_worldgen_v2) *(custom port)*
- **Other versions** → [modrinth.com/mod/voxy-worldgen](https://modrinth.com/mod/voxy-worldgen) *(official)*

### Photon Shaders — Reimagined
Custom Photon fork with native Physics Mod ocean support and Colorwheel (Create) shading — fully compatible with this Voxy build.

- **All versions** → [github.com/realBritakee/photon](https://github.com/realBritakee/photon) *(always up to date)*
