package ru.practicum.explorewithme.enums;

public enum ReactionType {
    LIKE("лайк"),
    DISLIKE("дизлайк");

    private String name;

    private ReactionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
