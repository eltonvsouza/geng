package br.com.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ProgressBarBean {
    private boolean enabled=false;
    private Integer total = 1;
    private Integer count = 0;
	    
    public ProgressBarBean() {
    }
    
    public String startProcess() {
    	setEnabled(true);
        setCount(0);
        setTotal(1);
        return null;
    }

    public Integer getCurrentValue(){
        if (isEnabled()){
        	if(total == count){
        		return 100;
        	}else{
	            return (100/total)*count;
        	}
        }
        return 0;
    }
    
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
    
}
