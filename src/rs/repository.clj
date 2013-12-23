(ns rs.repository
  (:import (org.bson.types ObjectId))
  (:require [rs.config :refer [cfg]]
            [monger.core :as mg]
            [monger.collection :as mc]
            [monger.conversion :refer [to-object-id]]
            [monger.json]
            [monger.joda-time]))

(mg/connect! (cfg :mongo))
(mg/use-db! (cfg :db))

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
      (assoc m :_id (str id))
      m)))

(defn things  "list all matching things"
  ([] (things {}))
  ([query] (map from-db (mc/find-maps "things" query))))

(defn thing  "get a single thing"
  [id]
  (->> id
       to-object-id
       (mc/find-map-by-id "things")
       from-db))

(defn create-thing [t]
  (->> t
       to-db
       (mc/insert-and-return "things")
       from-db))

(defn update-thing [t]
  ; NOTE: save-and-return is a little unsafe as it creates data if id not found
  (->> t
       to-db
       (mc/save-and-return "things")
       from-db))



