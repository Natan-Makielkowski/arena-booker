
<h1 id="arena-booker">Arena Booker</h1>
<p>A sports facility reservation system built with a fat-client architecture. The project consists of a native Windows desktop application (Flutter) and a containerized REST API (Spring Boot).</p>
<h2 id="tech-stack">Tech Stack</h2>
<p><strong>Backend:</strong></p>
<ul>
<li>
<p>Java 25</p>
</li>
<li>
<p>Spring Boot 3 (Web, Data JPA, Security, Validation)</p>
</li>
<li>
<p>JWT Authentication</p>
</li>
<li>
<p>H2 Database (in-memory)</p>
</li>
<li>
<p>Docker &amp; Docker Compose</p>
</li>
</ul>
<p><strong>Frontend:</strong></p>
<ul>
<li>
<p>Dart / Flutter</p>
</li>
<li>
<p>Target platform: Windows Desktop</p>
</li>
</ul>
<h2 id="running-the-application">Running the Application</h2>
<h3 id="backend-docker">Backend (Docker)</h3>
<p>The server environment is fully containerized. You do not need to install Java or Maven on your host machine.</p>
<ol>
<li>
<p>Ensure Docker Desktop is running.</p>
</li>
<li>
<p>Clone the repository and navigate to the root directory.</p>
</li>
<li>
<p>Start the container:</p>
</li>
</ol>
<p>Bash</p>
<pre><code>docker-compose up --build

</code></pre>
<p>The API and database will be available at <code>http://localhost:8080</code>.</p>
<h3 id="frontend-windows-client">Frontend (Windows Client)</h3>
<p><strong>Option A: Pre-compiled executable (No Flutter SDK required)</strong></p>
<ol>
<li>
<p>Navigate to the <code>Releases</code> section of this repository.</p>
</li>
<li>
<p>Download the latest <code>.zip</code> archive for Windows.</p>
</li>
<li>
<p>Extract the archive and run <code>arena_booker.exe</code>.</p>
</li>
</ol>
<p><strong>Option B: Development mode</strong></p>
<ol>
<li>
<p>Navigate to the <code>frontend</code> directory.</p>
</li>
<li>
<p>Fetch dependencies: <code>flutter pub get</code>.</p>
</li>
<li>
<p>Run the application: <code>flutter run -d windows</code>.</p>
</li>
</ol>
<h2 id="architecture--security-notes">Architecture &amp; Security Notes</h2>
<ul>
<li>
<p><strong>Stateless Authentication:</strong> Implemented via short-lived JWT tokens passed in the <code>Authorization</code> header.</p>
</li>
<li>
<p><strong>IDOR Prevention:</strong> Resource ownership validation is handled securely via the <code>Principal</code> object directly from the Spring Security Context, ignoring client-provided user IDs.</p>
</li>
<li>
<p><strong>Data Integrity:</strong> Ensured by <code>@Transactional</code> annotations on critical service methods and cascading entity state transitions (<code>CascadeType.ALL</code>) to prevent race conditions and orphaned records.</p>
</li>
<li>
<p><strong>Containerization:</strong> Utilizes a multi-stage Dockerfile to separate the Maven build phase from the lightweight JRE runtime environment, optimizing the final image size.</p>
</li>
</ul>

