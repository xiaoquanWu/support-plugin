package com.xiaoquan.gradle.plugin;

import org.gradle.api.Action;

public class NukeEnvExtension {

    private EnvDomain qa;

    private JibxDomain jibx;

//    private EnvDomain prod;

    public NukeEnvExtension(EnvDomain qa) {
        this.qa = qa;
    }

    public NukeEnvExtension(EnvDomain qa, JibxDomain jibx) {
        this.qa = qa;
        this.jibx = jibx;
    }

    public void setQa(EnvDomain qa) {
        this.qa = qa;
    }

    public EnvDomain getQa() {
        return qa;
    }

    public void QA(Action<EnvDomain> action) {
        action.execute(this.qa);
    }


    public void jibx(Action<JibxDomain> jibxDomainAction) {
        jibxDomainAction.execute(this.jibx);
    }

    public JibxDomain getJibx() {
        return jibx;
    }
}
