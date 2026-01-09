# Optimisation et Industrialisation des Performances d’un Backend Java

## Contexte du projet

Ce projet a pour objectif de mettre en œuvre une **démarche complète d’ingénierie de la performance backend**, appliquée à une application **Spring Boot**.  
L’approche ne se limite pas à des optimisations ponctuelles, mais couvre l’ensemble du cycle :

- Observabilité
- Tests de charge
- Analyse des métriques
- Identification d’anti-patterns
- Optimisations mesurées
- Industrialisation et perspectives de scalabilité

Le projet s’inscrit dans un cadre pédagogique orienté **MOE (Maîtrise d’Œuvre)** et vise à démontrer une capacité à **raisonner comme un ingénieur performance**.

---

## Objectifs

### Objectifs techniques
- Mettre en place une **observabilité complète** (JVM, HTTP, JDBC, GC)
- Générer une charge réaliste via **Apache JMeter**
- Identifier les **véritables causes** de dégradation des performances
- Corriger les **anti-patterns backend classiques**
- Mesurer l’impact de chaque optimisation via **Grafana**

### Objectifs méthodologiques
- Distinguer problème applicatif vs infrastructure
- Construire une **baseline de performance fiable**
- Adopter une démarche reproductible et mesurable
- Préparer le système à une montée en charge future

---

## Architecture technique

### Stack utilisée

- **Backend** : Spring Boot
- **ORM** : Hibernate / JPA
- **Base de données** : relationnelle (MySQL / PostgreSQL)
- **Sécurité** : JWT (stateless)
- **Cache applicatif** : Spring Cache
- **Observabilité** : Micrometer + Actuator
- **Monitoring** : Prometheus
- **Visualisation** : Grafana
- **Tests de charge** : Apache JMeter

### Architecture globale

<img width="1024" height="1024" alt="gGhLg3VJUlPDgHExSP9WD" src="https://github.com/user-attachments/assets/092e684b-b0c7-4f0e-8db3-99b6cbd5d8a0" />


## Observabilité & Monitoring

Les métriques sont exposées via :

/actuator/prometheus


Elles sont collectées par **Prometheus** et visualisées dans **Grafana** à l’aide d’un dashboard dédié backend.

### Métriques principales surveillées

- Temps de réponse HTTP (avg / p95 / p99)
- Throughput (req/s)
- CPU JVM (`process_cpu_usage`)
- Mémoire Heap / Non-Heap
- Pauses GC
- Threads JVM
- Pool JDBC HikariCP

### Exemples d’expressions PromQL

```promql
# p95 HTTP
histogram_quantile(
  0.95,
  rate(http_server_requests_seconds_bucket[1m])
)

# Throughput
rate(http_server_requests_seconds_count[1m])

# Heap JVM
jvm_memory_used_bytes{area="heap"}

# JDBC
hikaricp_connections_active

### Tests de charge – Apache JMeter
<img width="1024" height="1024" alt="RbxLApeCC0kmiDYjKeDeQ" src="https://github.com/user-attachments/assets/87e85ec3-f6a8-4aaf-b4bd-360162d2bbc5" />

### Baseline de performance (avant optimisation)

Les premiers tests ont mis en évidence :

Temps de réponse > 15 secondes

p95 très instable

Erreurs Socket closed

Heap JVM erratique

GC irrégulier

Analyse clé
Élément	État
CPU	🟢 OK
Pool JDBC	🟢 OK
GC	🟢 OK
Threads JVM	🟢 OK
Temps réponse	🔴 Critique

-  Conclusion : le problème est applicatif, pas infrastructurel.

### Anti-patterns identifiés

Recalcul systématique de données quasi statiques

N+1 Queries Hibernate / JPA

Pagination inefficace (chargement complet)

Over-fetching d’entités

Absence de cache applicatif

Tests de charge irréalistes

### Optimisations mises en place
1️. Cache applicatif (READ / WRITE)

Problème
Requête SQL identique exécutée à chaque appel de /dashboard.

Solution

@Cacheable("dashboard")
public long totalFinishedMatches() {
    return matchRepository.countByStatus("FINISHED");
}

@CacheEvict(value = "dashboard", allEntries = true)
public void createDummyMatch() {
    ...
}


Résultats

Suppression des accès DB en lecture

Latence stable

JDBC actif ≈ 0

2️. Anti-pattern N+1 Queries

Avant

for (Match m : matches) {
    m.getPlayer().getUsername();
}


Après – requête DTO optimisée

@Query("""
    SELECT new MatchPlayerDto(p.username)
    FROM Match m JOIN m.player p
""")


Résultats

1 seule requête SQL

Heap JVM stabilisé

Threads JVM maîtrisés

p95 fortement réduit

3️. Pagination efficace

Problème

findAll()

Pagination en mémoire

Solution

Pagination côté base (LIMIT / OFFSET)

DTO

Taille de page maîtrisée

Résultats

Réduction mémoire

GC plus stable

Threads constants (~24)

📊 Comparaison Avant / Après
Indicateur	Avant	Après
Latence p95	Instable (>15s)	< 30 ms
Pool JDBC	Risque saturation	Stable
Heap JVM	Erratique	Maîtrisé
Threads JVM	> 180	~24
Scalabilité	Faible	Bonne
### Sécurité & Performance

Authentification JWT

Backend stateless

Compatible cache et scalabilité horizontale

Aucun impact négatif significatif sur les performances

### Scalabilité & limites
Scalabilité actuelle

Stateless backend

Pool JDBC maîtrisé

Cache applicatif local

Limites identifiées

Pas de cache distribué

Base de données unique

Pas de rate limiting

Pas d’auto-scaling

### Perspectives d’amélioration

Cache distribué (Redis)

Alerting Prometheus

Auto-scaling

Rate limiting

Keyset pagination

Tests de montée en charge progressive

### Conclusion

Ce projet démontre une démarche complète d’ingénierie de la performance backend :

Observer avant d’optimiser

Corriger des anti-patterns mesurables

Valider chaque optimisation par des métriques

Construire un système stable, observable et industrialisable

-- L’optimisation n’est pas supposée, elle est prouvée.
