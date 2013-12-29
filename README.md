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

`lein midje` will run tests, once I have tests.

