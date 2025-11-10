// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;


contract FertiliAudit {
    // Events
    event ConsentRecorded(bytes32 indexed patientHash, string cid, address indexed issuer, uint256 timestamp);
    event AuditLogged(bytes32 indexed dataHash, string action, address indexed actor, uint256 timestamp);
    event AccessTokenIssued(uint256 indexed tokenId, bytes32 indexed subjectHash, address indexed grantee, uint256 expiry, string role);
    event AccessTokenRevoked(uint256 indexed tokenId, address indexed revokedBy, uint256 timestamp);

    // Simple authorization: owner pattern
    address public owner;
    mapping(address => bool) public authorized;

    modifier onlyOwner() {
        require(msg.sender == owner, "Not owner");
        _;
    }
    modifier onlyAuthorized() {
        require(authorized[msg.sender] || msg.sender == owner, "Not authorized");
        _;
    }

    constructor() {
        owner = msg.sender;
        authorized[msg.sender] = true;
    }

    function setAuthorized(address account, bool allow) external onlyOwner {
        authorized[account] = allow;
    }

    // --- Consent storage (only store cid pointer) ---
    mapping(bytes32 => string[]) private consentsByPatient;

    function recordConsent(bytes32 patientHash, string calldata cid) external onlyAuthorized {
        consentsByPatient[patientHash].push(cid);
        emit ConsentRecorded(patientHash, cid, msg.sender, block.timestamp);
    }

    function getConsents(bytes32 patientHash) external view returns (string[] memory) {
        return consentsByPatient[patientHash];
    }

    // --- Audit entries kept on-chain (both in storage and events) ---
    struct AuditEntry {
        bytes32 dataHash;
        string action;
        address actor;
        uint256 timestamp;
    }
    AuditEntry[] private audits;

    function recordAudit(bytes32 dataHash, string calldata action) external onlyAuthorized {
        audits.push(AuditEntry({dataHash: dataHash, action: action, actor: msg.sender, timestamp: block.timestamp}));
        emit AuditLogged(dataHash, action, msg.sender, block.timestamp);
    }

    function getAuditCount() external view returns (uint256) {
        return audits.length;
    }

    function getAudit(uint256 idx) external view returns (bytes32, string memory, address, uint256) {
        require(idx < audits.length, "index out of range");
        AuditEntry storage a = audits[idx];
        return (a.dataHash, a.action, a.actor, a.timestamp);
    }

    // --- Simple Access Tokens ---
    struct AccessToken {
        uint256 id;
        bytes32 subjectHash;
        address grantee;
        uint256 expiry;
        string role;
        bool active;
    }

    uint256 private nextTokenId = 1;
    mapping(uint256 => AccessToken) private tokens;

    function issueAccessToken(bytes32 subjectHash, address grantee, uint256 expiry, string calldata role) external onlyAuthorized returns (uint256) {
        require(grantee != address(0), "invalid grantee");
        require(expiry > block.timestamp, "expiry must be future");
        uint256 id = nextTokenId++;
        tokens[id] = AccessToken({id: id, subjectHash: subjectHash, grantee: grantee, expiry: expiry, role: role, active: true});
        emit AccessTokenIssued(id, subjectHash, grantee, expiry, role);
        return id;
    }

    function revokeAccessToken(uint256 tokenId) external onlyAuthorized {
        AccessToken storage t = tokens[tokenId];
        require(t.id != 0 && t.active, "token not active");
        t.active = false;
        emit AccessTokenRevoked(tokenId, msg.sender, block.timestamp);
    }

    function isTokenValid(uint256 tokenId) external view returns (bool) {
        AccessToken storage t = tokens[tokenId];
        if (t.id == 0) return false;
        return t.active && block.timestamp <= t.expiry;
    }

    function getToken(uint256 tokenId) external view returns (AccessToken memory) {
        return tokens[tokenId];
    }
}
