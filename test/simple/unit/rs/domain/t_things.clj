(ns unit.rs.domain.t_things
  (:require [midje.sweet :refer :all]
            [rs.domain.things :as subject]
            [rs.repository.thing-repository :as repo]))

(fact "getting a thing delegates to the repository"
      (subject/thing 123)
      => ..the-thing..
      (provided (repo/thing 123)
                => ..the-thing..))
