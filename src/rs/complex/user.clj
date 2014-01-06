(ns rs.complex.user
  (:require [rs.config :as config]
            [rs.complex.system :as system]
            [com.stuartsierra.component :as component]
            [clojure.tools.namespace.repl :refer (refresh)]))

(comment "this is just for development
 - see https://github.com/stuartsierra/component#reloading")

(def the-sys nil)

(defn init []
  (alter-var-root #'the-sys
                  (constantly (system/system (config/config)))))

(defn start []
  (alter-var-root #'the-sys component/start))

(defn stop []
  (alter-var-root #'the-sys
                  (fn [s] (when s (component/stop s)))))

(defn go []
  (init)
  (start))

(defn reset []
  (stop)
  (refresh :after 'rs.user/go))
