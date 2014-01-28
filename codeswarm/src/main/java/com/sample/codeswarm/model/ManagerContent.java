package com.sample.codeswarm.model;

import com.sample.codeswarm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample name for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ManagerContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DeveloperModel> ITEMS = new ArrayList<DeveloperModel>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DeveloperModel> ITEM_MAP = new HashMap<String, DeveloperModel>();

    static {
        // Add 3 sample items.
        addItem(new DeveloperModel("1", "Jackie Chan", "Kung-Fu Master", "50 Years", "Fights", R.drawable.jackie));
        addItem(new DeveloperModel("2", "Charlie Sheen", "Acting Manager", "30 Years", "Drinking", R.drawable.charlie));
        addItem(new DeveloperModel("3", "Jennifer Aniston", "Producer", "20 Years", "Acting", R.drawable.jen));
    }

    private static void addItem(DeveloperModel item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of name.
     */
    public static class DeveloperModel {
        public final String id;
        public final String name;
        public final String description;
        public final String experience;
        public final String strength;
        public final int imageId;

        public DeveloperModel(String id, String name, String description, String experience, String strength, int imageId) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.experience = experience;
            this.strength = strength;
            this.imageId = imageId;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getExperience() {
            return experience;
        }

        public String getStrength() {
            return strength;
        }

        public int getImageId() {
            return imageId;
        }
    }
}
