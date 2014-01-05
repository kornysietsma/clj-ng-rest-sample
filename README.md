# clj-ng-rest-sample - a sample clojure restful api + angular app

This is just somewhere for me to dump out the boilerplate I'd currently use if building a new REST api in clojure.

It also has an Angular UI - this really should be a separate project, except it's handy to have both things in a
single project, as it makes testing and deployment simple.

This is opinionated, and (of course) rushed - there are probably better ways to do this.  Some of it is based on
my memories of previous projects, but I'm trying to avoid copying anything directly from other code, to respect
client IP, and also as things have generally moved on since the client code was written!

NOTE: this (as of version 0.1.0) uses Stuart Sierra's component library for dependency management
you probably want to read https://github.com/stuartsierra/component for it to make sense.

This means the WebServer depends on a ThingDomain, which in turn depends on a ThingRepository
Both ThingDomain and ThingRepository are based around protocols, so you can substitute an alternative
for testing.

ThingDomain could be extended to do more than just wrap the single repository, obviously.

## running stuff
You need mongodb installed and running on the default port.

`lein run` will run on the default port (3000) - open http://localhost:3000 to see the app.

`lein midje` will run all tests
Note by default this includes the end to end tests.

There are aliases for unit tests:
`lein unit`
and integration:
`lein integration`

see the aliases in project.clj to see how these work.

## REPL-based development
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

