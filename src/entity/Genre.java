package entity;

import java.util.Arrays;
import java.util.Optional;

public enum Genre {
    Fantasy,
    Autobiography,
    NonFiction,
    ScienceFiction,
    TravelBooks,
    Antiutopia;

    public static Optional<Genre> find(String genre){
        return Arrays.stream(values())
                .filter(t -> t.name().equals(genre))
                .findFirst();
    }
}
