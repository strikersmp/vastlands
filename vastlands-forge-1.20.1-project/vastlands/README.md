# Vast Lands (Minecraft Forge 1.20.1)

An exploration-first world type plus dirt and grass slabs.

## Build the jar

Needs JDK 17 and internet access (Gradle downloads Forge and Minecraft the first time).

    ./gradlew build          # Windows: gradlew.bat build

The finished mod is `build/libs/vastlands-1.0.0.jar`. Drop it in `mods/` of a Forge 1.20.1 install (Forge 47.x).
To test in a dev client instead: `./gradlew runClient`.

### No local setup? Build it online

Create a free GitHub repository, upload everything in this folder (keep the `.github` folder), open the **Actions** tab,
run **Build jar**, and download the `vastlands-jar` artifact when it finishes (about 5 minutes). It contains the jar.

## Use it

Create a new world, press **World Type** and choose **Vast Lands**. Existing worlds are not changed.
Dedicated server: set `level-type=vastlands:vast_lands` in `server.properties` before the first start.

## What you get

| Feature | How it is built |
|---|---|
| Plains and savannas tens of thousands of blocks across, no trees, rare grass | custom biomes `vastlands:vast_plains` / `vast_savanna`, climate noise wavelength about 30,000 blocks |
| Forests are rare but dense | vanilla `forest` only where the humidity noise is very high (about 5% of the map) |
| Big hills | a hill mask noise adds rolling hills up to about 100 blocks tall on roughly 16% of the land |
| Rare, enormous oceans | continent noise at about 30,000 block scale, oceans cover about 5% of the map |
| Rare, enormous mountains | mountain noise at about 30,000 block scale, mountains cover about 2.5% of the map, bases 10,000+ blocks wide |
| Mountain zones | forest and taiga at the foot, then snowy conifer grove, snowy slopes, and frozen / jagged peaks, driven by a temperature that falls with altitude |
| Very detailed mountains | ridge lines in 2D plus two 3D noises for crags and overhangs |
| Survival playable | vanilla ores, caves, strongholds, villages, mineshafts, mobs. Spawn is steered towards open plains |
| Dirt Slab / Grass Slab | craft 3 dirt or 3 grass blocks into 6 slabs. Grass slabs are tinted like grass, decay when buried, and dirt slabs grow grass from nearby grass |

## Height limit

Minecraft 1.20.1 cannot build higher than Y = 2031 (engine limit, 4064 blocks total). The world here runs from Y = -64
to Y = 2031, so the tallest mountains are about 1,800 blocks above the plains. A true 4,000 block mountain would need the
sea level moved to the very bottom of the world, which breaks ores, caves and strongholds.

## Tuning

Everything is plain JSON under `src/main/resources/data/vastlands/worldgen/`:

* `density_function/continent.json`, `m_raw.json`: the `xz_scale` of 0.55 sets how large oceans and mountains are.
  Lower it for even bigger features.
* `density_function/m_raw.json`: the two `ramp` numbers (0.28 and 0.95) set how rare mountains are. Raise 0.28 for fewer.
* `density_function/mountain_profile.json`: height of the mountain flanks and summit.
* `noise/temperature.json` and `humidity.json`: `firstOctave` -15 gives the 30,000 block biome size.
* `world_preset/vast_lands.json`: which biome appears at which climate value.

## Notes

* Chunk generation is slower than vanilla because the world is 2096 blocks tall.
* The terrain data was checked with a Python re-implementation of Minecraft's noise and density functions, not in the
  game itself. Treat the first launch as a test run.
