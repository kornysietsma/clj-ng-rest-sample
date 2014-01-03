(ns unit.rs.t-repository
  (:require [midje.sweet :refer :all]
            [somnium.congomongo :as m]
            [rs.repository :as subject])
  (:import (org.bson.types ObjectId)))

(def thing-id (ObjectId/get))
(def thing-id-str (.toString thing-id))

(facts "about the thing repository"
  (against-background
    (m/make-connection anything) => irrelevant
    (m/connection? anything) => true)

  (fact "getting a thing converts Mongo id to a string"
    (subject/thing thing-id-str)
    => {:_id thing-id-str :foo "bar"}
    (provided
      (m/fetch-by-id :things thing-id)
      => {:_id thing-id :foo "bar"}))
  )
