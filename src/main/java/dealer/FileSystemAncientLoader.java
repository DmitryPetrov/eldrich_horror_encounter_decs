package main.java.dealer;

import main.java.horror.GameBase;
import main.java.horror.ancient.AncientName;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class FileSystemAncientLoader {

    private final String COMPONENTS_PATH = "./components";
    private final String ANCIENT_PATH = "ancients";
    private final List<GameBase> bases;
    private Set<File> selectedBaseDirs = new HashSet<>();
    private final Map<GameBase, String> gameBasePaths = new HashMap<>();

    public FileSystemAncientLoader(List<GameBase> bases) {
        this.bases = new ArrayList<>(bases);
        gameBasePathSetUp();
        getBaseDirs();
    }

    private void gameBasePathSetUp() {
        gameBasePaths.put(GameBase.ORIGIN, GameBase.ORIGIN.name().toLowerCase(Locale.ROOT));
        gameBasePaths.put(GameBase.FORSAKEN_LORE, GameBase.FORSAKEN_LORE.name().toLowerCase(Locale.ROOT));
        gameBasePaths.put(GameBase.MOUNTAINS_OF_MADNESS, GameBase.MOUNTAINS_OF_MADNESS.name().toLowerCase(Locale.ROOT));
        gameBasePaths.put(GameBase.STRANGE_REMNANTS, GameBase.STRANGE_REMNANTS.name().toLowerCase(Locale.ROOT));
        gameBasePaths.put(GameBase.UNDER_THE_PYRAMIDS, GameBase.UNDER_THE_PYRAMIDS.name().toLowerCase(Locale.ROOT));
        gameBasePaths.put(GameBase.SIGNS_OF_CARCOSA, GameBase.SIGNS_OF_CARCOSA.name().toLowerCase(Locale.ROOT));
        gameBasePaths.put(GameBase.DREAMLANDS, GameBase.DREAMLANDS.name().toLowerCase(Locale.ROOT));
        gameBasePaths.put(GameBase.CITIES_IN_RUIN, GameBase.CITIES_IN_RUIN.name().toLowerCase(Locale.ROOT));
    }

    private void getBaseDirs() {
        File[] dirs = new File(COMPONENTS_PATH).listFiles();
        if (dirs == null || dirs.length == 0) {
            throw new RuntimeException(COMPONENTS_PATH + " folder is empty");
        }
        for (File dir: dirs) {
            if (bases
                    .stream()
                    .map(gameBasePaths::get)
                    .anyMatch(path -> path.equals(dir.getName()))
            ) {
                selectedBaseDirs.add(dir);
            }
        }
    }

    public Map<String, byte[]> getAncientCards(AncientName name) {
        Map<String, byte[]> result = new HashMap<>();
        for (File baseDir: selectedBaseDirs) {
            File ancientDir = openDir(baseDir, ANCIENT_PATH);
            File selectedAncientDir = openDir(ancientDir, name.name().replace("_", "-"));
            File[] ancientFiles = selectedAncientDir.listFiles();
            for (File file: ancientFiles) {
                result.put(file.getName(), readFileContent(file));
            }
        }
        return result;
    }

    private File openDir(File baseDir, String dirName) {
        File[] dirs = baseDir.listFiles((dir, name) -> name.equalsIgnoreCase(dirName));
        if (dirs == null || dirs.length == 0) {
            throw new RuntimeException(baseDir.getPath() + " does not contain [" + dirName + "] dir");
        }
        return dirs[0];
    }

    private byte[] readFileContent(File file) {
        try {
            return new FileInputStream(file).readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Read card error: " + file.getPath() + file.getName());
    }
}
