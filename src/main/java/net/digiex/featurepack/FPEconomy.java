package net.digiex.featurepack;

import net.digiex.featurepack.payment.Methods;

public class FPEconomy {

    private FeaturePack parent;
    private Methods Methods = null;

    public FPEconomy(FeaturePack parent) {
        this.parent = parent;
        this.Methods = new Methods();
    }

    public void load() {
        if (!this.Methods.hasMethod()) {
            if (this.Methods.setMethod(this.parent)) {
                this.parent.Method = this.Methods.getMethod();
                FeaturePack.log.info("Using " + this.parent.Method.getName()
                        + " version " + this.parent.Method.getVersion());
            }
        }
    }
}