(ns unit.rs.domain.t_things
  (:require [midje.sweet :refer :all]
            [rs.domain.things :as subject]
            [rs.repository.thing-repository :as repo]))

(def stub-domain {:database :database})

(fact "getting a thing delegates to the repository"
      (subject/thing stub-domain 123) => ..result..
      (provided
        (repo/thing :database 123) => ..result..))

