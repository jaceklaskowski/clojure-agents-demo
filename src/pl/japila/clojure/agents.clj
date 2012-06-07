(ns pl.japila.clojure.agents)

(def agents (map agent (range 0 5)))

(defn pick-agent [as]
  (let [idx (rand-int (count as))]
    (nth as idx)))

(defn log []
  (printf "Agent: %s on thread: %s %n" *agent* (Thread/currentThread)))

(defn dec-msg [s as]
  (let [_ (log)]
    (cond
      (pos? s) (let [_ (println ">>> pos?")
                     a (pick-agent as)]
                 (send a dec-msg as)
                 (dec s))
      :default (let [_ (println ">>> :default")
                     fas (remove (partial = *agent*) as)
                     a (pick-agent fas)]
                 (send a dec-msg fas)
                 s))))
  
(send (pick-agent agents) dec-msg agents)

