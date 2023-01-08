<div>
    <label for="file_attacher"> select file to insert </label>
    <br/>
    <input bind:files id="file_attacher" multiple type="file" accept=".txt,.csv,.xls,.xlsx"/>
</div>
{#if files}
    <h2>Selected files:</h2>
    {#each Array.from(files) as file}
        <p>{file.name} ({file.size} bytes)</p>
    {/each}
{/if}

<script>
 let files;

 $: if(files) {
    console.log(files);

    for (const file of files) {
        console.log(`${file.name}: ${file.size} bytes`);
    }
 }
</script>