package com.tcc.cti.driver;

public class Operator {

    final String _companyId;
    final String _opId;

    public Operator(String companyId, String opId) {
        _companyId = companyId;
        _opId = opId;
    }

    public String getCompanyId() {
        return _companyId;
    }

    public String getOpId() {
        return _opId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
            + ((_companyId == null) ? 0 : _companyId.hashCode());
        result = prime * result + ((_opId == null) ? 0 : _opId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Operator other = (Operator) obj;
        if (_companyId == null) {
            if (other._companyId != null) {
                return false;
            }
        } else if (!_companyId.equals(other._companyId)) {
            return false;
        }
        if (_opId == null) {
            if (other._opId != null) {
                return false;
            }
        } else if (!_opId.equals(other._opId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OperatorKey [_companyId=");
        builder.append(_companyId);
        builder.append(", _opId=");
        builder.append(_opId);
        builder.append("]");
        return builder.toString();
    }
}
