(ns rs.repository.thing-repository
  (:import (org.bson.types ObjectId))
  (:require [somnium.congomongo :as m]))

(defn- to-object-id [s] (ObjectId. s))
(defn- from-object-id [o] (.toString o))

(defn- to-db "convert map to mongo representation with object id"
  [m]
  (let [id (:_id m)]
    (if id
      (assoc m :_id (to-object-id id))
      m)))

(defn- from-db "convert db map to map with string id"
  [m]
  (let [id (:_id m)]
    (if id
      (assoc m :_id (from-object-id id))
      m)))

(defprotocol ThingRepositoryProtocol
  (things [this] [this query] "all the things")
  (thing [this id] "a single thing")
  (create-thing [this t] "create a thing")
  (update-thing [this new-t] "update a thing"))

(defrecord ThingRepository [database]
  ThingRepositoryProtocol
  (things
    [this] (things this {}))
  (things
    [this query]
    (m/with-mongo (:connection database)
                  (map from-db
                       (m/fetch :things :where query))))

  (thing
    [this id]
    (m/with-mongo (:connection database)
                  (->> id
                       to-object-id
                       (m/fetch-by-id :things)
                       from-db)))

  (create-thing [this t]
    (m/with-mongo (:connection database)
                  (->> t
                       to-db
                       (m/insert! :things)
                       from-db)))

  (update-thing [this new-t]
    (m/with-mongo (:connection database)
                  (let [id (to-object-id (:_id new-t))]
                    (->> new-t
                         to-db
                         (m/fetch-and-modify :things {:_id id})
                         from-db)))))

(defn new-thing-repository []
  (->ThingRepository nil))


