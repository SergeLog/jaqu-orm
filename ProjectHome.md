<p>
An orm framework based on Jaqu framework (<a href='http://www.h2database.com/html/jaqu.html'>http://www.h2database.com/html/jaqu.html</a>) originally written by Thomas Mueller. The Original framework has been enhanced as follows:<br>
<p>
<ol>
<li>Support for H2, MYSQL, POSTGRESQL, ORACLE, SQL SERVER, DB/2 databases.</li>
<li>Support One2One, One2Many, Many2Many relationships (But only with<br>
single primary keys for now), in select, insert, update & delete.<br />
(O2M, M2M require the jaqu plugin for development, or use the supplied ant task. they also have a development time dependency on asm.jar)</li>
<li>Supports transient fields (i.e field you don't want to work with<br>
persistence)</li>
<li>Lazy loading of o2m, m2m relationships</li>
<li>Supports cascade deletes on relationships</li>
<li>Works with private fields as well as public fields</li>
<li>Writing an update like the following: db.from(p).set(p.name, value).where(p.field).is(value).update();</li>
<li>Writing a primary-Key select: db.from(p).primaryKey().is(value).select();</li>
<li>Support for connection pool to a single db. and set the commit option...</li>
<li>Added commit, rollback.</li>
<li>Addressed some thread safety issues</li>
</ol>