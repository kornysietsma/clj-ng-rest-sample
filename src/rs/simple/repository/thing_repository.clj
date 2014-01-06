(ns rs.simple.repository.thing-repository
  (:import (org.bson.types ObjectId))
  (:require [rs.config :refer [cfg]]
            [somnium.congomongo :as m]))

(def conn
  (m/make-connection (cfg :mongo :db)
                     :host (cfg :mongo :host)
                     :port (cfg :mongo :port)))

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

(defn things "list all matching things"
  ([] (things {}))
  ([query] (map from-db
                (m/with-mongo conn
                              (m/fetch :things :where query)))))

(defn thing "get a single thing"
  [id]
  (m/with-mongo conn
                (->> id
                     to-object-id
                     (m/fetch-by-id :things)
                     from-db)))

(defn create-thing [t]
  (m/with-mongo conn
                (->> t
                     to-db
                     (m/insert! :things)
                     from-db)))

(defn update-thing [new-t]
  (m/with-mongo conn
                (let [id (to-object-id (:_id new-t))]
                  (->> new-t
                       to-db
                       (m/fetch-and-modify :things {:_id id})
                       from-db))))
