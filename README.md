# clj-ng-rest-sample - a sample clojure restful api + angular app

This is just somewhere for me to dump out the boilerplate I'd currently use if building a new REST api in clojure.  Well, two boilerplates, but more on that later.

It also has an Angular UI - this really should be a separate project, except it's handy to have both things in a
single project, as it makes testing and deployment simple.

This is opinionated, and (of course) rushed - there are probably better ways to do this.  Some of it is based on
my memories of previous projects, but I'm trying to avoid copying anything directly from other code, to respect
client IP, and also as things have generally moved on since the client code was written!

## Complex vs Simple

As of 0.2.0 there are two different implementations of the server included:

- a simple one, that uses global namespace references and a global database - no DI, no clever logic.  But it's simple.
You have to test this one with mocks, and if you forget to mock something like the database, the real db will be used.

- a complex one, that uses Stuart Sierra's component library for dependency management - it's more extensible,
and you can test cleanly by injecting stub or mock dependencies.  It's a better pattern for bigger apps - but may be overkill if you just want a simple microservice.

The choice of simple/complex is managed through Leiningen profiles "simple" and "complex".  The complex version is the default in normal development mode - you can use "with-profile simple" to run against the simple app.  See https://github.com/technomancy/leiningen/blob/stable/doc/PROFILES.md for more on how profiles work.

## running stuff
You need mongodb installed and running on the default port.

`lein complex` will run the complex app on the default port (3000) - open http://localhost:3000 to see the app.

`lein simple` will run the simple app, on the same port and URL.

`lein midje` will run all tests against the complex project.
Note by default this includes the end to end tests.

`lein with-profile simple midje` will run all tests against ths simple project.

There are aliases for unit tests:
`lein unit`
and integration:
`lein integration`

see the aliases in project.clj to see how these work.  You can work out how to run the simple versions of these yourself.

## REPL-based development - for the complex app
To start a repl `lein repl`
to load the magic reloading workflow stuff: `(require 'rs.user)`
to start the system: `(rs.user/go)`
to stop the system: `(rs.user/stop)`
to reload: `(rs.user/reset)`

Something is still broken running midje tests in the repl, but reset works now.

## other notes

The single index.html is just for simplicity - you could template this up with one of many tools, but as my
sample only has one (real) page, using one html page is easy.  Need to fix this if you want login forms or
nice 404 pages or similar.

## libraries/frameworks used

The API uses compojure for routing, liberator for REST, Midje for tests

Stuart Sierra's component library is used for dependency and lifecycle management: https://github.com/stuartsierra/component

Hiccup is included for html templating, but not really used - the main page is just static html for now.

Web side uses the Kraken framework for a relatively lightweight css framework, Angular.js for all the work.

Some of the Angular stuff is based on Angular Seed: https://github.com/angular/angular-seed
