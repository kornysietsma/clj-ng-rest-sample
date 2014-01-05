(ns unit.rs.repository.t-thing-repository
  (:require [midje.sweet :refer :all]
            [somnium.congomongo :as m]
            [rs.repository.thing-repository :as subject])
  (:import (org.bson.types ObjectId)))

(def thing-id (ObjectId/get))
(def thing-id-str (.toString thing-id))

(def stub-repo (subject/->ThingRepository {:connection :stub-conn}))

(facts "about the thing repository"
  (against-background
    ; stops the m/with-mongo macro complaining
    (m/connection? :stub-conn) => true)

  (fact "getting a thing converts Mongo id to a string"
    (subject/thing stub-repo thing-id-str)
    => {:_id thing-id-str :foo "bar"}
    (provided
      (m/fetch-by-id :things thing-id)
      => {:_id thing-id :foo "bar"})))
