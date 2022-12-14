# Adding Content
## Adding an Enchantment
1. Add cost and possibly other configurable stuff about the enchantment to `MagicConfig.java`.
2. Add the `public final static` enchantment variable ending with `_ENCHANTMENT` to `MagicContent.java`.
3. Register that variable in `MagicContent.java` under `registerEnchantments()`.
4. Add the enchantment effect to `WandItem.java`.
5. Add the enchantment to `any_spell.json` and `all_spells.json` following the respective format of the other enchantments there.
6. Add the enchantment description to `en_us.json`.

## Adding a Projectile Enchantment
1. Follow the steps from "Adding an Enchantment", leaving out step 4.
2. Subclass from `MagicProjectileEntity.java`, implement the two constructors and override `applyEntityEffects(Entity)` and `applyBlockEffects(BlockHitResult)` as needed.
3. Add a corresponding `public final static` entity type variable ending with `_ENTITY_TYPE` to `MagicContent.java`, with registration via `registerEntityType(...)`.
4. If needed, add a texture into `textures/entities/projectiles`.
5. Register the new entity type in `MagicClient.java`.
6. Add a `public static` `create(World, PlayerEntity)` to your entity class, returning an instance of it created (but not yet spawned) in the world. See missiles for examples.
7. Call that method in `WandItem.java` like the missiles call theirs.