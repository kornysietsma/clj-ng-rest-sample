(ns rs.complex.domain.things
  (:require [rs.complex.repository.thing-repository :as repository]))

(defprotocol ThingDomainProtocol
  (things [this] [this query] "all the things")
  (thing [this id] "a single thing")
  (create-thing [this t] "create a thing")
  (update-thing [this new-t] "update a thing"))

; Note: Domain is stateless so doesn't need to implement component/LifeCycle
(defrecord ThingDomain [config thing-repository]
  ThingDomainProtocol
  (things [this] (things this {}))

  (things [this query] (repository/things thing-repository query))

  (thing [this id]
    (repository/thing thing-repository id))

  (create-thing [this t]
    (repository/create-thing thing-repository t))

  (update-thing [this new-t]
    (repository/update-thing thing-repository new-t)))

(defn new-thing-domain [config]
  (->ThingDomain config nil))

