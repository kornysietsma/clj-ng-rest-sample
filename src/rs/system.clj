(ns rs.system
  (:require
    [rs.repository]
    [rs.domain]
    [rs.web]
    [rs.config :as config]
    [com.stuartsierra.component :as component]))

(def system-components [:webserver :repository :domain])

(defrecord RestSystem [webserver repository domain]
  component/Lifecycle
  (start [this]
    (component/start-system this system-components))
  (stop [this]
    (component/stop-system this system-components)))

(defn system [conf]
  (let [{{ws-host :host ws-port :port} :webserver
         {m-host :host m-port :port m-db :db} :mongo} conf]
    (map->RestSystem
      {:repository (rs.repository/new-repository m-host m-port m-db)
       :domain (component/using
                  (rs.domain/new-domain conf)
                  [:repository])
       :webserver (component/using
                    (rs.web/web-server ws-host ws-port)
                    [:domain])})))

(defn -main [& args]
  (let [conf (config/config)]
    (component/start
      (system conf))))