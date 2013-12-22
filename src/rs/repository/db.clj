(ns rs.repository.db
  (:require [rs.config :refer [cfg]]
            [monger.core :as mg]
            [monger.collection :as mc]
            [monger.json]
            [monger.joda-time]))

(mg/connect! (cfg :db))
(mg/use-db! "rest-sample")

(defn things
  ([] (things {}))
  ([query] (mc/find-maps "things" query)))

(defn add-thing [thing]
  (mc/insert-and-return "things" thing))


