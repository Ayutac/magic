# Adding Content
## Adding an Enchantment
1. Add cost and possibly other configurable stuff about the enchantment to `MagicConfig.java`.
2. Add an enum entry to `Spell.java`.
3. Add the enchantment effect to `WandItem.java`.
4. Add the enchantment to `any_spell.json` and `all_spells.json` following the respective format of the other enchantments there.
5. Add the enchantment description to `en_us.json`.

## Adding a Projectile Enchantment
1. Follow the steps from "Adding an Enchantment", leaving out step 3.
2. Subclass from `MagicProjectileEntity.java`, implement the two constructors and override `applyEntityEffects(Entity)` and `applyBlockEffects(BlockHitResult)` as needed.
3. If needed, add a texture into `textures/entities/projectiles`.
4. Add a `public static` `create(World, PlayerEntity)` to your entity class, returning an instance of it created (but not yet spawned) in the world. See missiles for examples.
5. Add that method to your enum entry in `Spell.java`.