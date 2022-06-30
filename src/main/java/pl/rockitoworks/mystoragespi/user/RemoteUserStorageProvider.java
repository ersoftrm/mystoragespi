package pl.rockitoworks.mystoragespi.user;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import pl.rockitoworks.mystoragespi.client.RemoteKeycloakClientProvider;

import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class RemoteUserStorageProvider implements
        UserStorageProvider,
        UserLookupProvider,
        CredentialInputValidator
{

    public static final String EXTERNAL_USER_ID_ATTRIBUTE = "externalUserIdAttribute";
    private final KeycloakSession session;
    private final ComponentModel componentModel;
    private final Function<ComponentModel, ResteasyClient> clientFactory;
    private final ConcurrentMap<String, RemoteKeycloakClientProvider> remoteKeycloakProviderCache;

    public RemoteUserStorageProvider(
            KeycloakSession session,
            ComponentModel componentModel,
            Function<ComponentModel, ResteasyClient> clientFactory,
            ConcurrentMap<String, RemoteKeycloakClientProvider> remoteKeycloakProviderCache) {
        this.session = session;
        this.componentModel = componentModel;
        this.clientFactory = clientFactory;
        this.remoteKeycloakProviderCache = remoteKeycloakProviderCache;
    }


    @Override
    public boolean supportsCredentialType(String credentialType) {
        return false;
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        return false;
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public UserModel getUserById(String id, RealmModel realm) {
        return null;
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
        return null;
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
        return null;
    }
}
