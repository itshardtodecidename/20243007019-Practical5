public class TextProcessor {

    /**
     * Validates and normalises a municipality name.
     *
     * @param municipality the raw user input
     * @return the correctly capitalised name (e.g. "Beijing"),
     *         or null if the input is null, empty, whitespace-only
     *         or not a supported municipality.
     */
    public String processMunicipality(String municipality) {
        // Reject null, empty or whitespace-only input
        if (municipality == null || municipality.trim().isEmpty()) {
            return null;
        }

        // Compare case-insensitively, return the canonical spelling
        String trimmed = municipality.trim();
        if (trimmed.equalsIgnoreCase("beijing")) {
            return "Beijing";
        } else if (trimmed.equalsIgnoreCase("shanghai")) {
            return "Shanghai";
        } else if (trimmed.equalsIgnoreCase("tianjin")) {
            return "Tianjin";
        } else if (trimmed.equalsIgnoreCase("chongqing")) {
            return "Chongqing";
        }

        // Unsupported municipality, e.g. Guangzhou
        return null;
    }
}
