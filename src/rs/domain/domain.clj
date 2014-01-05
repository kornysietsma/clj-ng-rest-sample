(ns rs.domain.domain)

; Note: Domain is stateless so doesn't need to implement component/LifeCycle
; currently it's just a holder for the database and config info (which isn't used yet)
(defrecord Domain [config database])

(defn new-domain [config]
  (->Domain config nil))
