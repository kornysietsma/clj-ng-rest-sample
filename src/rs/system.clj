(ns rs.system
  (:require
    [rs.repository.database :as database]
    [rs.repository.thing-repository :as thing-repository]
    [rs.domain.things :as things]
    [rs.web]
    [rs.config :as config]
    [com.stuartsierra.component :as component]))

(def system-components [:webserver :database :thing-repository :thing-domain])

(defrecord RestSystem [webserver database thing-repository thing-domain]
  component/Lifecycle
  (start [this]
    (component/start-system this system-components))
  (stop [this]
    (component/stop-system this system-components)))

(defn system [conf]
  (let [{{ws-host :host ws-port :port} :webserver
         {m-host :host m-port :port m-db :db} :mongo} conf]
    (map->RestSystem
      {:webserver (component/using
                    (rs.web/web-server ws-host ws-port)
                    [:thing-domain])
       :thing-domain (component/using
                  (things/new-thing-domain conf)
                  [:thing-repository])
       :thing-repository (component/using
                  (thing-repository/new-thing-repository)
                  [:database])
       :database (database/new-database m-host m-port m-db)})))

(defn -main [& args]
  (let [conf (config/config)]
    (component/start
      (system conf))))