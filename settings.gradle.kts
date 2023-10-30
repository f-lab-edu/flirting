rootProject.name = "flirting"

include(":app-service-api", ":app-event-consumer")
include(":domain", ":domain:domain-rds", ":domain:domain-redis")
include(":gateway")
include(":core")
include(":notification")