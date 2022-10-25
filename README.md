# LDAP Injection Story

**To read**: [https://cheatsheetseries.owasp.org/cheatsheets/LDAP_Injection_Prevention_Cheat_Sheet.html]

**Estimated reading time**: 10 min

## Story Outline

This story is focused on providing guidance for preventing LDAP Injection flaws
in your applications.

LDAP Injection is an attack used to exploit web-based applications that
construct LDAP statements based on user input.
When an application fails to properly sanitize user input,
it's possible to modify LDAP statements through techniques like SQL Injection.

LDAP injection attacks could result in the granting of permissions to
unauthorized queries, and content modification inside the LDAP tree.

## Story Organization

**Story Branch**: master
>`git checkout master`

**Practical task tag for self-study**: task
>`git checkout task`

Tags: #injection, #security, #ldap
