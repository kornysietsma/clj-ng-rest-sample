(ns unit.rs.complex.domain.t_things
  (:require [midje.sweet :refer :all]
            [rs.complex.domain.things :as subject]
            [rs.complex.repository.thing-repository :as repo]))

; this shows how to test with a stubbed repo, no mocks needed

(fact "getting a thing delegates to the repository"
      (let [stub-repo (reify repo/ThingRepositoryProtocol
                        (thing [this id] (str "thing:" id)))
            domain (subject/->ThingDomain {:config :none} stub-repo)]

        (subject/thing domain 123)
        => "thing:123"))
