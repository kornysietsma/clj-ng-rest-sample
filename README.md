# clj-ng-rest-sample - a sample clojure restful api + angular app

This is just somewhere for me to dump out the boilerplate I'd currently use if building a new REST api in clojure.

It also has an Angular UI - this really should be a separate project, except it's handy to have both things in a
single project, as it makes testing and deployment simple.

This is opinionated, and (of course) rushed - there are probably better ways to do this.  Some of it is based on
my memories of previous projects, but I'm trying to avoid copying anything directly from other code, to respect
client IP, and also as things have generally moved on since the client code was written!

## running stuff
You need mongodb installed and running on the default port.

`lein ring server` will run a server.

`lein midje` will run all tests
Note by default this includes the end to end tests.

There are aliases for unit tests:
`lein unit`
and integration:
`lein integration`

see the aliases in project.clj to see how these work.

## notes

The single index.html is just for simplicity - you could template this up with one of many tools, but as my
sample only has one (real) page, using one html page is easy.  Need to fix this if you want login forms or
nice 404 pages or similar.

## libraries/frameworks used

Clojure uses compojure for routing, liberator for REST, Midje for tests

Hiccup is included for html templating, but not really used - the main page is just static html for now.

Web side uses the Kraken framework for a relatively lightweight css framework, Angular.js for all the work.

