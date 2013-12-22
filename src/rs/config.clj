(ns rs.config
  (:require [clojure.edn :as edn]))

(defn- read-config []
  (edn/read-string (slurp (clojure.java.io/resource "config.edn"))))

(def config (memoize read-config))

(defn cfg [& keys] (get-in (config) keys))
