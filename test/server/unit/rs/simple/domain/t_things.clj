(ns unit.rs.simple.domain.t_things
  (:require [midje.sweet :refer :all]
            [rs.simple.domain.things :as subject]
            [rs.simple.repository.thing-repository :as repo]))

(fact "getting a thing delegates to the repository"
      (subject/thing 123)
      => ..the-thing..
      (provided (repo/thing 123)
                => ..the-thing..))
