(ns rs.domain.things
  (:require [rs.repository.thing-repository :as repository]))

(defn things
  ([] (things {}))
  ([query] (repository/things query)))

(defn thing [id]
  (repository/thing id))

(defn create-thing [t]
  (repository/create-thing t))

(defn update-thing [new-t]
  (repository/update-thing new-t))

