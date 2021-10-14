package com.dani.movie;

import java.time.LocalDate;
import java.util.List;

import com.dani.actor.Actor;

public record Movie(Integer id,
                    String name,
                    List<Actor> actors,
                    LocalDate releaseDate) {
}
