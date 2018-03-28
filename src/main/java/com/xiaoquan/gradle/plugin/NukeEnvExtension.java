package com.xiaoquan.gradle.plugin;

import org.gradle.api.Action;

public class NukeEnvExtension {

    private EnvDomain qa;

//    private EnvDomain prod;

    public NukeEnvExtension(EnvDomain qa) {
        this.qa = qa;
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
}
