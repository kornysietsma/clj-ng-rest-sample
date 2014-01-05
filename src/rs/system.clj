(ns rs.system
  (:require
    [rs.repository.database :as database]
    [rs.domain.domain :as domain]
    [rs.web]
    [rs.config :as config]
    [com.stuartsierra.component :as component]))

(def system-components [:webserver :database :domain])

(defrecord RestSystem [webserver database domain]
  component/Lifecycle
  (start [this]
    (component/start-system this system-components))
  (stop [this]
    (component/stop-system this system-components)))

(defn system [conf]
  (let [{{ws-host :host ws-port :port} :webserver
         {m-host :host m-port :port m-db :db} :mongo} conf]
    (map->RestSystem
      {:database (database/new-database m-host m-port m-db)
       :domain (component/using
                  (domain/new-domain conf)
                  [:database])
       :webserver (component/using
                    (rs.web/web-server ws-host ws-port)
                    [:domain])})))

(defn -main [& args]
  (let [conf (config/config)]
    (component/start
      (system conf))))