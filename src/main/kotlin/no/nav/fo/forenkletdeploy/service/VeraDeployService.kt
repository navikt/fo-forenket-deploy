package no.nav.fo.forenkletdeploy.service

import no.nav.fo.forenkletdeploy.VeraDeploy
import no.nav.fo.forenkletdeploy.consumer.VeraConsumer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*
import javax.inject.Inject

@Service
class VeraDeployService @Inject
constructor(
        val teamService: TeamService,
        val veraConsumer: VeraConsumer
) {
    private val LOG = LoggerFactory.getLogger(VeraDeployService::class.java)
    private val gyldigeMiljoer = Arrays.asList("p", "q0", "q10", "q6", "t6", "t10")

    fun getDeploysForTeam(teamId: String): List<VeraDeploy> =
            teamService.getAppsForTeam(teamId)
                    .flatMap { getDeploysForApp(it.name) }

    fun getDeploysForApp(app: String): List<VeraDeploy> =
            try {
                veraConsumer.getDeploysForApp(app)
                        .map {
                            VeraDeploy(it.id, it.application, it.deployed_timestamp, it.version,
                                    it.environment,
                                    it.deployer, it.environmentClass, it.replaced_timestamp)}
                        .filter { gyldigeMiljoer.contains(it.environment) }
            } catch (e: Throwable) {
                LOG.error("Kunne ikke hente deploys for $app", e)
                throw e
            }
}
