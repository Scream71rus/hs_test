# hs_test

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

To start tests, run:

    lein test
    or
    lein test test/hs_test/models/patient_model_test.clj 

To start REPL, run:

    lein repl

To start in docker, run:    

    docker-compose up --build

## License

Copyright © 2020 FIXME
