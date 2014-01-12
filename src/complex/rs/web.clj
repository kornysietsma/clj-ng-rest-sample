(ns rs.web
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [rs.resources :as resources]
            [rs.pages :as pages]
            [ring.adapter.jetty :as jetty]
            [com.stuartsierra.component :as component]))

(defn parameterised-routes [thing-domain]
  (defroutes app-routes
             (GET "/healthcheck" [] (resources/healthcheck thing-domain))
             (ANY "/things" [] (resources/things thing-domain))
             (ANY ["/thing/:id", :id #".+"] [id]
                  (resources/thing thing-domain id))

             (GET "/" [] (pages/index))
             (route/resources "/")
             (route/not-found {:status 404 :body "nothing to see here, move along"})
             ))

(defn app [thing-domain]
  (-> (handler/api (parameterised-routes thing-domain))
      (json/wrap-json-body {:keywords? true})))

(defrecord WebServer [host port thing-domain]
  component/Lifecycle

  (start [this]
    (println "--> Starting WebServer")
    (assoc this :server (jetty/run-jetty (app thing-domain) {:host host :port port :join? false})))

  (stop [this]
    (println "--> Stopping WebServer")
    (.stop (:server this))
    this))

(defn web-server [host port]
  (map->WebServer {:host host :port port}))


