DROP TABLE IF EXISTS pokemons;

CREATE TABLE IF NOT EXISTS pokemons(
      id INTEGER PRIMARY KEY,
      num TEXT NOT NULL,
      name TEXT NOT NULL,
      height DOUBLE NOT NULL,
      weight DOUBLE NOT NULL
);